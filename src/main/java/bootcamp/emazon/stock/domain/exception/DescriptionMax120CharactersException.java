package bootcamp.emazon.stock.domain.exception;

public class DescriptionMax120CharactersException extends RuntimeException{
    public DescriptionMax120CharactersException(){super();}

    public static class BrandAlreadyExistsException extends RuntimeException{
        public BrandAlreadyExistsException(){super();}
    }
}
