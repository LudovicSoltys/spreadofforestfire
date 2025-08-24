package com.lso.sandbox.simulator.cells.list.facade;

import com.lso.sandbox.simulator.repositories.data.CellJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.data.CellJpaEntity;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class AllCellsReadingFacade implements AllCellsSupplier {

    private final CellJpaCrudRepository repository;

    public AllCellsReadingFacade(CellJpaCrudRepository repository) {
        this.repository = repository;
    }

    @Override
    public Either<Errors, AllCells> get() {
        Iterable<CellJpaEntity> entities = repository.findAll();

        List<Cell> list = IterableUtils.streamOf(entities)
                .map((Function<CellJpaEntity, Cell>) SimpleCell::new)
                .toList();

        return Either.right(new AllCellsImpl(list));
    }

    static class SimpleCell implements Cell {

        private final CellJpaEntity entity;

        public SimpleCell(CellJpaEntity entity) {
            this.entity = entity;
        }

        @Override
        public boolean isAlive() {
            return entity.isAlive();
        }

        @Override
        public boolean isBurning() {
            return entity.isBurning();
        }

        @Override
        public boolean isDead() {
            return entity.isDead();
        }

        @Override
        public int getX() {
            return entity.getX();
        }

        @Override
        public int getY() {
            return entity.getY();
        }
    }

    static class AllCellsImpl implements AllCells {

        private final Iterable<Cell> items;

        public AllCellsImpl(Iterable<Cell> items) {
            this.items = items;
        }

        @Override
        public Iterator<Cell> iterator() {
            return items.iterator();
        }
    }
}
