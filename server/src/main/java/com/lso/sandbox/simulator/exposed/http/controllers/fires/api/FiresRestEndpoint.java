package com.lso.sandbox.simulator.exposed.http.controllers.fires.api;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.CellChanges;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Coordinates;
import com.lso.sandbox.simulator.exposed.http.shared.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/fires")
public interface FiresRestEndpoint {

    @PostMapping("/next")
    FiresChangeResponse propagateFire(FiresPropagationRequest request);

    @PostMapping
    FiresChangeResponse addFires(FiresAddRequest request);

    @GetMapping
    FireListResponse listFires();

    interface FiresAddRequest {

        List<CellChanges> getTargets();
    }

    interface FiresChangeResponse extends Response {

        Iterable<CellChanges> getChanges();
    }

    class FiresPropagationRequest {
        private boolean dryRun = false;

        public boolean isDryRun() {
            return dryRun;
        }

        public void setDryRun(boolean dryRun) {
            this.dryRun = dryRun;
        }
    }

    interface FireListResponse extends Response {

        Iterable<Coordinates> getContent();

        long getTotalElements();
    }
}
