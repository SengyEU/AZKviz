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

    private boolean player1 = true;

    private final int[][] buttonStates = new int[7][7];

    public void start(){
        initializeButtonStates();
        new HraciPole(this);
    }

    public boolean isPlayer1Playing(){
        return player1;
    }

    public void setPlayer1Playing(boolean player1) {
        this.player1 = player1;
    }

    public void play() {
        //TODO: Implement winning system
    }

    public void initializeButtonStates(){
        for(int i = 1; i <= 7; i++){
            for(int j = 0; j < i; j++){
                buttonStates[i-1][j] = 1;
            }
        }
    }

    public int[][] getButtonStates() {
        return buttonStates;
    }

    public void setButtonState(int state, int row, int col){
        buttonStates[row][col] = state;
    }

    public int getButtonState(int row, int col){
        return buttonStates[row][col];
    }


}
