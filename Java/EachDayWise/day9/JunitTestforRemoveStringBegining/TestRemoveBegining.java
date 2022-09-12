import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestRemoveBegining {

    //for input that will have both first 2 and last 2 similar substrings
    @Test
    public void checkForPositiveInput(){
        assertEquals("din",RemoveBegining.removeSubstring("indin"));
    }


    //for inputs that doesnt have simliar substring of length 2 at begining and end
    @Test
    public void checkForNegetiveInput(){
        assertNotEquals("din",RemoveBegining.removeSubstring("windin"));
    }







}