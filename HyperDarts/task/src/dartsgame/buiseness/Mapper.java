package dartsgame.buiseness;

public interface Mapper<T, R> {
    R map(T entity);
}