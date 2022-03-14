package dartsgame.buiseness;

public interface Mapper<T, U> {
    U mapToEntity(T dto);

    T mapToDTO(U entity);
}