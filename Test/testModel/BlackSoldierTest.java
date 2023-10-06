package testModel;

import boardifier.model.ElementTypes;
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

public class BlackSoldierTest {
    GameStageModel gameStageModel;

    @BeforeEach
    public void setUp() {
        gameStageModel = mock(GameStageModel.class);
    }

    @Test
    public void testBlackSoldierConstructor() {
        BlackSoldier blackSoldier = new BlackSoldier(gameStageModel,1);
        Assertions.assertEquals(51,blackSoldier.getType());
        }
    }


