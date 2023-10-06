package testModel;

import boardifier.model.ElementTypes;
import model.Throne;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import boardifier.model.GameStageModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


import java.util.Scanner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class TroneTest {

    GameStageModel gameStageModel;

    @BeforeEach
    public void setUp() {
        gameStageModel = mock(GameStageModel.class);
    }

    @Test
    public void testTroneConstructor() {
        Throne trone = new Throne(gameStageModel);
        Assertions.assertEquals(55, trone.getType());
    }
}

