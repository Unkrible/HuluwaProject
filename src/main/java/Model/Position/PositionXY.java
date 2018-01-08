package Model.Position;

public class PositionXY extends Position {
    protected int y;
    private int N; // 记录二维数组的一位维度的大小


    public PositionXY(int y, int x, int N){
        super(x);
        this.y = y;
        this.N = N;
    }

    public PositionXY(int index, int N){
        super(index % N);
        this.N = N;
        this.y = index / N;
    }

    public int getY(){
        return this.y;
    }

    //MARK: Position
    @Override
    public int index(){
        if(x < N)
            return y * N + x;
        else
            return 0;
    }

    @Override
    public String toString(){
        return y+ ","+x;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(this==obj){
            return true;
        }
        if(obj instanceof PositionXY){
            PositionXY posXY=(PositionXY)obj;
            return x == posXY.getX() && y == posXY.getY();
        }
        return false;
    }
}
