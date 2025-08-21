package com.lso.sandbox.simulator.cells;

import com.lso.sandbox.simulator.cells.list.CellsListUseCase;
import com.lso.sandbox.simulator.shared.Message;
import com.lso.sandbox.simulator.shared.Response;
import com.lso.sandbox.simulator.shared.validation.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.lso.sandbox.simulator.cells.list.facade.AllCellsSupplier.AllCells;
import static com.lso.sandbox.simulator.cells.list.facade.AllCellsSupplier.Cell;

@RestController
@RequestMapping("/api/cells")
public class CellsController {

    private static final Logger LOG = LoggerFactory.getLogger(CellsController.class);

    private final CellsListUseCase listUseCase;

    public CellsController(CellsListUseCase listUseCase) {
        this.listUseCase = listUseCase;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CellsListResponse all() {

        LOG.debug("Message=Trying to retrieve all cells;");

        CellsListUseCase.Input input = new CellsListInputImpl();
        CellsListResponseImpl output = new CellsListResponseImpl();

        listUseCase.execute(input, output);

        LOG.debug("Message=Operation of retrieving all cells is done;Result={}", output);

        return output;
    }

    interface CellsListResponse extends Response {

        List<CellsListResponseItem> getItems();

        int getTotalElements();
    }

    interface CellsListResponseItem {
        int getX();

        int getY();

        boolean isBurning();

        boolean isDead();
    }

    static class CellsListInputImpl implements CellsListUseCase.Input {

    }

    static class CellsListResponseImpl implements CellsListUseCase.Output, CellsListResponse {

        private final List<CellsListResponseItem> items = new ArrayList<>();

        private final List<Message> messages = new ArrayList<>();

        private final List<Message> errors = new ArrayList<>();

        @Override
        public List<Message> getMessages() {
            return List.of();
        }

        @Override
        public List<Message> getErrors() {
            return List.of();
        }

        @Override
        public List<CellsListResponseItem> getItems() {
            return items;
        }

        @Override
        public int getTotalElements() {
            return items.size();
        }

        @Override
        public void accept(AllCells data) {

            data.forEach(element -> items.add(new CellsListResponseItemImpl(element)));

            if (items.isEmpty()) {
                messages.add(Message.of("list.empty"));
            }
        }

        @Override
        public void reject(Errors errors) {

            errors
                    .getAllErrors()
                    .forEach(item -> this.errors.add(Message.of(item.getCode(), item.getArguments(), item.getDefaultMessage())));
        }
    }

    static class CellsListResponseItemImpl implements CellsListResponseItem {

        private final Cell data;

        public CellsListResponseItemImpl(Cell data) {
            this.data = data;
        }

        @Override
        public int getX() {
            return data.getX();
        }

        @Override
        public int getY() {
            return data.getY();
        }

        @Override
        public boolean isBurning() {
            return data.isBurning();
        }

        @Override
        public boolean isDead() {
            return data.isDead();
        }
    }
}
