package cls.exceptions;

public class UnAuthorizedException extends Exception{
    public UnAuthorizedException(){

    }

    public UnAuthorizedException(String message){
        super(message);
    }
}
