package portfolio.Upload.Storage;

public class StorageException extends RuntimeException
{
    public StorageException(String msg)
    {
        super(msg);
    }
    public StorageException(String msg, Throwable err)
    {
        super(msg, err);
    }

}
