package testModel;

import boardifier.model.ElementTypes;
import model.KingPawn;
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


public class KingPawnTest {

    GameStageModel gameStageModel;

    @BeforeEach
    public void setUp() {
        gameStageModel = mock(GameStageModel.class);
    }

    @Test
    public void testKingPawnConstructor() {
        KingPawn kingPawn = new KingPawn(gameStageModel,1);
        Assertions.assertEquals(53, kingPawn.getType());
        Assertions.assertEquals(2,kingPawn.getColor());
    }


}