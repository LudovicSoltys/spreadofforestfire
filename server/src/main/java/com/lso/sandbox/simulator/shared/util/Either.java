package com.lso.sandbox.simulator.shared.util;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Either<L, R> {

    static <L, R> Either<L, R> right(R right) {
        return new Right<>(right);
    }

    static <L, R> Either<L, R> left(L left) {
        return new Left<>(left);
    }

    boolean isLeft();

    boolean isRight();

    R get();

    L getLeft();

    default <U> U fold(Function<? super L, ? extends U> leftMapper, Function<? super R, ? extends U> rightMapper) {
        Objects.requireNonNull(leftMapper, "leftMapper is null");
        Objects.requireNonNull(rightMapper, "rightMapper is null");

        if (isRight()) {
            return rightMapper.apply(get());
        } else {
            return leftMapper.apply(getLeft());
        }
    }

    default <U> Either<L, U> map(Function<? super R, ? extends U> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");

        if (isRight()) {
            return Either.right(mapper.apply(get()));
        } else {
            return (Either<L, U>) this;
        }
    }

    default <U> Either<L, U> flatMap(Function<? super R, Either<L, U>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");

        if (isLeft()) {
            return (Either<L, U>) this;
        }

        return mapper.apply(get());
    }

    default void then(Consumer<? super L> leftAction, Consumer<? super R> rightAction) {
        Objects.requireNonNull(leftAction, "leftAction is null");
        Objects.requireNonNull(rightAction, "rightAction is null");

        if (isLeft()) {
            leftAction.accept(getLeft());
        } else { // this isRight() by definition
            rightAction.accept(get());
        }
    }

    final class Right<L, R> implements Either<L, R>, Serializable {

        private final R value;

        private Right(R value) {
            this.value = value;
        }

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public boolean isRight() {
            return true;
        }

        @Override
        public R get() {
            return value;
        }

        @Override
        public L getLeft() {
            throw new NoSuchElementException(("getLeft() on Right"));
        }
    }

    final class Left<L, R> implements Either<L, R>, Serializable {

        private final L value;

        private Left(L value) {
            this.value = value;
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public boolean isRight() {
            return false;
        }

        @Override
        public R get() {
            throw new NoSuchElementException(("get() on Left"));
        }

        @Override
        public L getLeft() {
            return value;
        }
    }
}
