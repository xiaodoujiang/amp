package cn.bmilk.amp.ampService.exception;

/**
 * 乐观锁异常类
 */
public class OptimisticLockingException extends RuntimeException{
    public OptimisticLockingException(){
        super();
    }
    public OptimisticLockingException(String message){
        super(message);
    }
}
