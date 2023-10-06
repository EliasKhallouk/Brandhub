package testControl;

import boardifier.control.Controller;
import boardifier.control.ControllerKey;
import boardifier.model.GameStageModel;
import boardifier.model.GridElement;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.view.RootPane;
import boardifier.view.View;
import control.BrandubhController;
import javafx.stage.Stage;
import model.BlackSoldier;
import model.KingPawn;
import model.WhitSoldier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.BufferedReader;

import static org.mockito.Mockito.*;

public class BrandubControllerTest {

    @Mock
    private Model model;

    @Mock
    private GridElement grid;

    @Mock
    private View view;

    @Mock
    private Player currentPlayer;

    @Mock
    private BufferedReader consoleIn;
    BrandubhController brandubController;
    GameStageModel gameStageModel;


    @BeforeEach
    public void setup() {
        model = mock(Model.class);

        Stage mockStage = Mockito.mock(Stage.class);
        RootPane rootPane = Mockito.mock(RootPane.class);  // Create a new RootPane

        View view = Mockito.mock(View.class);
        gameStageModel = mock(GameStageModel.class);

        brandubController = mock(BrandubhController.class);
    }

    @Test
    public void cooOfKing() {
        brandubController = Mockito.mock(BrandubhController.class);
        GridElement grid = mock(GridElement.class);
        KingPawn k = mock(KingPawn.class);
        Mockito.when(grid.getElement(1, 1)).thenReturn(k);
        int[] t = {1, 1};
        Assertions.assertArrayEquals(t, brandubController.cooOfKing(grid));
    }

    @Test
    public void testVerifDuelWithWhitSoldier() {
        int col = 3;
        int row = 3;
        // Arrange
        GridElement grid = mock(GridElement.class);
        WhitSoldier whiteSoldier = mock(WhitSoldier.class);
        Mockito.when(grid.getElement(2, 3)).thenReturn(whiteSoldier);
        Mockito.when(grid.getElement(3, 3)).thenReturn(mock(BlackSoldier.class));
        Mockito.when(grid.getElement(1, 3)).thenReturn(mock(BlackSoldier.class));
        Mockito.when(grid.getElement(2, 2)).thenReturn(mock(BlackSoldier.class));
        Mockito.when(grid.getElement(2, 4)).thenReturn(mock(BlackSoldier.class));

        String result = brandubController.verifDuel(2, 3, grid);

        // Assert
        Assertions.assertEquals("YYYY", result);
    }

    @Test
    public void testVerifDuelWithBlackSoldier() {
        int col = 3;
        int row = 3;
        // Arrange
        GridElement grid = mock(GridElement.class);
        Mockito.when(grid.getElement(3, 3)).thenReturn(mock(BlackSoldier.class));
        Mockito.when(grid.getElement(2, 3)).thenReturn(mock(WhitSoldier.class));
        Mockito.when(grid.getElement(4, 3)).thenReturn(mock(WhitSoldier.class));
        Mockito.when(grid.getElement(3, 2)).thenReturn(mock(WhitSoldier.class));
        Mockito.when(grid.getElement(3, 4)).thenReturn(mock(WhitSoldier.class));
        // Act
        String result = brandubController.verifDuel(3, 3, grid);

        // Assert
        Assertions.assertEquals("YYYY", result);
    }

    @Test
    public void testVerifDuelWithKingPawn() {
        // Arrange
        GridElement grid = mock(GridElement.class);
        Mockito.when(grid.getElement(3, 3)).thenReturn(mock(KingPawn.class));
        Mockito.when(grid.getElement(2, 3)).thenReturn(mock(BlackSoldier.class));
        Mockito.when(grid.getElement(4, 3)).thenReturn(mock(BlackSoldier.class));
        Mockito.when(grid.getElement(3, 2)).thenReturn(mock(WhitSoldier.class));
        Mockito.when(grid.getElement(3, 4)).thenReturn(mock(WhitSoldier.class));

        // Act
        String result = brandubController.verifDuel(3, 3, grid);

        // Assert
        Assertions.assertEquals(null, result);
    }

    @Test
    public void testVerifDuelWithInvalidCoordinates() {
        // Arrange
        GridElement grid = mock(GridElement.class);

        // Act
        String result = brandubController.verifDuel(7, 7, grid);

        // Assert
        Assertions.assertEquals("NNNN", result);
    }



    @Test
    public void testCaseDispo() {
        // Initialisation
        int rowControl = 1;
        int colControl = 1;
        int rowInitial = 2;
        int colInitial = 2;
        boolean reel = true;
        char color = 'W';
        boolean isEmptyAt = true;
        boolean setCellReachable = true;

        GridElement grid = mock(GridElement.class);
        when(grid.isEmptyAt(rowControl, colControl)).thenReturn(isEmptyAt);
        when(grid.getElement(anyInt(), anyInt())).thenReturn(grid);
        when(grid.isEmptyAt(anyInt(), anyInt())).thenReturn(true);

        BlackSoldier mockBlackSoldier = mock(BlackSoldier.class);
        when(grid.getElement(rowInitial, colInitial)).thenReturn(mockBlackSoldier);

        String result = brandubController.caseDispo(rowControl, colControl, rowInitial, colInitial, reel, color, grid);

        verify(grid, times(1)).isEmptyAt(rowControl, colControl);
        verify(grid, times(1)).isEmptyAt(anyInt(), anyInt());
        verify(grid, times(1)).setCellReachable(rowInitial, colInitial, false);
        verify(grid, never()).setCellReachable(anyInt(), anyInt(), true);

        Assertions.assertEquals("manger", result);
    }

    @Test
    public void testNbTrueReachableCells() {
        // Création d'un mock pour GridElement
        GridElement grid = Mockito.mock(GridElement.class);

        // Création d'une matrice de cells atteignables
        boolean[][] reachableCells = {
                {true, true, false, true, false, false, true},
                {false, true, true, false, false, true, true},
                {true, false, true, true, false, true, true},
                {false, false, true, true, true, true, false},
                {false, true, false, false, true, true, true},
                {true, false, true, false, true, true, true},
                {true, true, true, true, false, true, true}
        };

        // Définition du comportement du mock GridElement
        Mockito.when(grid.getReachableCells()).thenReturn(reachableCells);

        // Appel de la méthode à tester
        int result = brandubController.nbTrueReachableCells(grid);

        // Vérification des résultats
        Assertions.assertEquals(0, result);
    }


    @Test
    public void testCellAround() {
        // Création d'un mock pour GridElement
        GridElement grid = Mockito.mock(GridElement.class);

        // Définition des paramètres de la méthode
        int row = 3;
        int col = 3;
        char color = 'W';

        // Définition du comportement du mock GridElement
        Mockito.when(grid.getElement(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);


        // Appel de la méthode à tester
        String result = brandubController.cellAround(row, col, color, grid);

        // Vérification du résultat
        Assertions.assertEquals(null, result);
    }



    @Test
    public void testCanEat() {
        // Création d'un mock pour GridElement
        GridElement grid = Mockito.mock(GridElement.class);

        // Définition des paramètres de la méthode
        int row = 3;
        int col = 3;


        Mockito.when(brandubController.cellAround(Mockito.anyInt(), Mockito.anyInt(), Mockito.eq('W'), Mockito.any(GridElement.class))).thenReturn("42");

        // Définition du comportement de canReachCell()
        Mockito.when(grid.canReachCell(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

        // Création d'un mock pour la méthode removeElement()
        GridElement blackSoldier = Mockito.mock(GridElement.class);
        Mockito.when(grid.getElement(Mockito.anyInt(), Mockito.anyInt())).thenReturn(blackSoldier);

        // Appel de la méthode à tester
        String result = brandubController.canEat(row, col, grid);

        // Vérification du résultat
        Assertions.assertEquals("3" + "3", result);

        // Vérification que removeElement() a été appelé avec le bon paramètre
        Mockito.verify(grid).removeElement(blackSoldier);
    }

    @Test
    public void testDeplacementDispo() {
        // Création d'un mock pour GridElement
        GridElement grid = Mockito.mock(GridElement.class);

        // Définition des paramètres de la méthode
        int col = 2;
        int row = 3;

        // Définition du comportement de isEmptyAt() pour les cases vides
        Mockito.when(grid.isEmptyAt(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

        // Définition du comportement de getElement() pour les cases contenant Escape
        GridElement escape = Mockito.mock(GridElement.class);
        Mockito.when(grid.getElement(Mockito.anyInt(), Mockito.anyInt())).thenReturn(escape);

        // Appel de la méthode à tester
        brandubController.deplacementDispo(col, row, grid);

        // Vérification des cases marquées comme accessibles
        Mockito.verify(grid).setCellReachable(3, 2, true); // Case vide
        Mockito.verify(grid).setCellReachable(2, 2, true); // Case vide
        Mockito.verify(grid).setCellReachable(1, 2, true); // Case vide
        Mockito.verify(grid).setCellReachable(0, 2, true); // Case vide
        Mockito.verify(grid).setCellReachable(4, 2, false); // Case non vide
        Mockito.verify(grid).setCellReachable(5, 2, false); // Case non vide

        // Vérification des appels aux méthodes isEmptyAt() et getElement()
        Mockito.verify(grid, Mockito.times(4)).isEmptyAt(Mockito.anyInt(), Mockito.eq(2));
        Mockito.verify(grid, Mockito.times(2)).getElement(Mockito.anyInt(), Mockito.eq(2));
    }

    @Test
    public void testVerfiDepla() {
        // Création d'un mock pour GridElement
        GridElement grid = Mockito.mock(GridElement.class);

        // Définition des paramètres de la méthode
        int col = 2;
        int row = 3;
        int col2 = 2;
        int row2 = 5;

        // Définition du comportement de resetReachableCells()
        Mockito.doNothing().when(grid).resetReachableCells(Mockito.anyBoolean());

        // Définition du comportement de canReachCell() pour la case cible
        Mockito.when(grid.canReachCell(Mockito.eq(row2), Mockito.eq(col2))).thenReturn(true);

        // Appel de la méthode à tester
        boolean result = brandubController.verfiDepla(col, row, col2, row2, grid);

        // Vérification du résultat
        Assertions.assertFalse(result);

        // Vérification des appels aux méthodes resetReachableCells() et canReachCell()
        Mockito.verify(grid).resetReachableCells(false);
        Mockito.verify(grid).canReachCell(row2, col2);
    }

    @Test
    public void testGetAllReachableCells() {
        // Création d'un objet GridElement simulé avec Mockito
        GridElement grid = mock(GridElement.class);

        // Création d'une matrice simulée de cellules atteignables
        boolean[][] reachableCells = new boolean[7][7];
        reachableCells[3][3] = true;
        reachableCells[4][3] = true;
        when(grid.getReachableCells()).thenReturn(reachableCells);

        BrandubhController test = mock(BrandubhController.class);

        // Appel de la méthode à tester
        test.getAllReachableCells(grid);

        // Vérification que la méthode a été appelée une fois
        verify(grid, times(1)).getReachableCells();

    }

    @Test
    public void testCheckKing() {
        // Create a mock GridElement object
        GridElement grid = mock(GridElement.class);

        // Create an instance of the class under test

        // Test direction 1
        int direction1 = 1;
        int row1 = 2;
        int col1 = 3;
        when(grid.getElement(row1 + 1, col1)).thenReturn(mock(GridElement.class));
        brandubController.checkKing(direction1, row1, col1, grid);
        verify(grid, times(1)).removeElement(grid.getElement(row1 + 1, col1));

        // Test direction 2
        int direction2 = 2;
        int row2 = 4;
        int col2 = 3;
        when(grid.getElement(row2 - 1, col2)).thenReturn(mock(GridElement.class));
        brandubController.checkKing(direction2, row2, col2, grid);
        verify(grid, times(1)).removeElement(grid.getElement(row2 - 1, col2));

        // Test direction 3
        int direction3 = 3;
        int row3 = 3;
        int col3 = 2;
        when(grid.getElement(row3, col3 + 1)).thenReturn(mock(GridElement.class));
        brandubController.checkKing(direction3, row3, col3, grid);
        verify(grid, times(1)).removeElement(grid.getElement(row3, col3 + 1));

        // Test direction 4
        int direction4 = 4;
        int row4 = 3;
        int col4 = 4;
        when(grid.getElement(row4, col4 - 1)).thenReturn(mock(GridElement.class));
        brandubController.checkKing(direction4, row4, col4, grid);
        verify(grid, times(1)).removeElement(grid.getElement(row4, col4 - 1));
    }

    @Test
    public void testVerifWin() {
        GridElement grid = mock(GridElement.class);
        Assertions.assertEquals(0, brandubController.verifKingWin(grid));
    }

    @Test
    public void testVerifWin2() {
        GridElement grid = mock(GridElement.class);
        Assertions.assertEquals(0, brandubController.verifKingWin(grid));
    }

    @Test
    public void testVerifWin3() {
        GridElement grid = mock(GridElement.class);
        Assertions.assertEquals(0, brandubController.verifKingWin(grid));
    }

    @Test
    public void testVerifWin4() {
        GridElement grid = mock(GridElement.class);
        Assertions.assertEquals(0, brandubController.verifKingWin(grid));
    }

    @Test
    public void testVerifWinlose() {
        GridElement grid = mock(GridElement.class);
        Assertions.assertEquals(0, brandubController.verifKingWin(grid));
    }

    @Test
    void testWillBeEaten() {
        // Create a mock GridElement
        GridElement grid = Mockito.mock(GridElement.class);

        // Set up the necessary conditions for the test
        int row = 2;
        int col = 3;
        boolean reel = true;
        char color = 'W';

        // Define the expected behavior of the mock dependencies
        Mockito.when(grid.getElement(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(new BlackSoldier(gameStageModel,1));


        // Call the method and assert the result
        Assertions.assertFalse(brandubController.willBeEaten(row, col, reel, color, grid));
    }
    @Test
    void testCheckFinishWin() {
        // Create a mock GridElement
        GridElement grid = Mockito.mock(GridElement.class);

        // Set up the necessary conditions for the test
        when(model.getIdWinner()).thenReturn(-1);

        // Call the method
        brandubController.checkfinishwin(grid);

        // Verify the expected behavior
        verify(brandubController, times(1)).verifKingWin(grid);
        verify(brandubController, times(1)).blackWinCheck(grid);
    }

    @Test
    void testRandomDests() {
        // Create a mock GridElement
        GridElement grid = Mockito.mock(GridElement.class);


        // Set up the necessary conditions for the test
        when(grid.canReachCell(anyInt(), anyInt())).thenReturn(true);
        when(brandubController.nbTrueReachableCells(grid)).thenReturn(5); // Set the number of true reachable cells

        // Call the method
        int[] result = brandubController.randomDests(grid);

        // Verify the expected behavior
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.length);
        Assertions.assertTrue(result[0] >= 0 && result[0] < 7); // Check if the column index is within range
        Assertions.assertTrue(result[1] >= 0 && result[1] < 7); // Check if the row index is within range
        verify(grid, atLeastOnce()).canReachCell(anyInt(), anyInt());
        verify(brandubController, times(1)).nbTrueReachableCells(grid);
    }
    @Test
    void testBlackWinCheck() {
        // Create mock dependencies
        GridElement grid = Mockito.mock(GridElement.class);

        when(grid.isEmptyAt(3, 3)).thenReturn(true);

        // Call the method
        int result = brandubController.blackWinCheck(grid);

        // Verify the expected behavior
        verify(grid, times(1)).isEmptyAt(3, 3);
        Assertions.assertEquals(2, result);
    }

    @Test
    void testCheckBlackPlayer() {
        // Create mock dependencies
        GridElement grid = Mockito.mock(GridElement.class);

        // Set up the necessary conditions for the test
        when(brandubController.KingOnGrid(grid)).thenReturn(false);

        // Call the method
        brandubController.checkblackplayer(grid);

        // Verify the expected behavior
        verify(brandubController, times(1)).KingOnGrid(grid);
    }

}
