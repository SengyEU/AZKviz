package azkviz;

import azkviz.gui.HraciPole;

public class AzKviz {


    /**
     * 0 - empty (not used)
     * 1 - available
     * 2 - player 1
     * 3 - player 2
     * 4 - yes/no
     */
    private final int[][] buttonStates = new int[7][7];

    public void start(){
        new HraciPole();
    }

    public void initializeButtonStates(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                buttonStates[i][j] = 0;
            }
        }
    }

    public void setButtonState(int state, int row, int col){
        buttonStates[row][col] = state;
    }

    public int getButtonState(int row, int col){
        return buttonStates[row][col];
    }

}
