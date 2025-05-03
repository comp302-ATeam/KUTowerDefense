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


}
