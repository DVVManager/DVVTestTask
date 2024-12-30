package pages;

public interface Navigational {

    boolean isOpened();

    //Navigational open(){ return  this};

    default void goBack(){};

    default void goForward(){};

    default void refresh(){};
}
