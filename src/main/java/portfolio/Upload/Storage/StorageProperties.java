package portfolio.Upload.Storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties("storage")
public class StorageProperties 
{
    private String location = "upload-dir";

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }
    
}
