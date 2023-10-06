package testModel;

import boardifier.model.ElementTypes;
import model.WhitSoldier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import boardifier.model.GameStageModel;
import model.BlackSoldier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


import java.util.Scanner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class WhiteSoldierTest{

    static int color;

    GameStageModel gameStageModel;

    @BeforeEach
    public void setUp() {
        gameStageModel = mock(GameStageModel.class);
    }

    @Test
    public void testBlackSoldierConstructor() {
        WhitSoldier whiteSoldier = new WhitSoldier(gameStageModel,1);
        Assertions.assertEquals(52,whiteSoldier.getType());

    }




}
