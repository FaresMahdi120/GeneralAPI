package mc.thearcade.commons.utils;

public final class Pair<L, R> {

    private final L left;
    private final R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    public Pair<L, R> setLeft(L left) {
        return new Pair<>(left, right);
    }

    public Pair<L, R> setRight(R right) {
        return new Pair<>(left, right);
    }

    public Pair<R, L> swap() {
        return new Pair<>(right, left);
    }

}
