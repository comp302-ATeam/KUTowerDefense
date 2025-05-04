package Domain.GameFlow;

import java.io.Serializable;

public class Vector2<T> implements Serializable { ///  BASIC VECTOR CLASS

    public T x;
    public T y;


    private static final long serialVersionUID = 1L;

    public Vector2(T x, T y) {
        this.x = x;
        this.y = y;
    }

    

    @Override
    public String toString() {
        return "Vector2 [x=" + x + ", y=" + y + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector2) {
            Vector2 other = (Vector2)obj;
            return x.equals(other.x) && y.equals(other.y);
        }
        return false;
    }
}
