package portfolio.Upload.Storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorage implements StorageService
{
    private final Path rootLocation;
    @Autowired
    public FileSystemStorage(StorageProperties properties)
    {
        if(properties.getLocation().trim().length() == 0)
        {
            throw new StorageException("file upload loaction string is empty");
        }
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file)
    {
        try
        {
            if(file.isEmpty())
            {
                throw new StorageException("File is empty: failed to store");
            }
            Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
            if(!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath()))
            {
                throw new StorageException("can't store outside the current direcotry");
            }
            try(InputStream inputStream = file.getInputStream())
            {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }catch(IOException e)
        {
            throw new StorageException("failed to store the file due to: " + e.getMessage());
        }
    }
    @Override
    public Stream<Path> loadAll()
    {
        try
        {
            return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation)).map(this.rootLocation::relativize);
        }
        catch(IOException e)
        {
            throw new StorageException("Failed to read stored files: ", e);
        }
    }

    @Override
    public Path load(String filename)
    {
        return this.rootLocation.resolve(filename);
    }

    @Override 
    public Resource loadAsResource(String filename)
    {
        try
        {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable())
            {
                return resource;
            }
            else
            {
                throw new StorageException("couldn't read file: " + filename);
            }
        }catch(MalformedURLException e)
        {
            throw new StorageException("couldn't read file "+filename + "due to :", e);
        }        
    }
    public void deleteAll()
    {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
    public void init()
    {
        try
        {
            Files.createDirectories(rootLocation);
        }catch(IOException e)
        {
            throw new StorageException("Could not initialize storage because: ", e);
        }
    }

}
