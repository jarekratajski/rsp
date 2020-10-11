package rsp;

import rsp.dom.Path;
import rsp.dom.RemoteDomChangesPerformer;
import rsp.dsl.WindowDefinition;
import rsp.server.OutMessages;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

public class EventContext {

    private final Map<Integer, CompletableFuture<String>> registeredEventHandlers;
    private final OutMessages out;
    private final Supplier<Integer> descriptorSupplier;
    private final Function<Object, Path> pathLookup;

    public EventContext(Supplier<Integer> descriptorSupplier,
                        Map<Integer, CompletableFuture<String>> registeredEventHandlers,
                        Function<Object, Path> pathLookup,
                        OutMessages out) {
        this.descriptorSupplier = descriptorSupplier;
        this.registeredEventHandlers = registeredEventHandlers;
        this.pathLookup = pathLookup;
        this.out = out;
    }

    public PropertiesHandle props(Ref ref) {
        return new PropertiesHandle(ref);
    }

    public CompletableFuture<String> evalJs(String js) {
        final Integer newDescriptor = descriptorSupplier.get();
        final CompletableFuture<String> resultHandler = new CompletableFuture<>();
        registeredEventHandlers.put(newDescriptor, resultHandler);
        out.evalJs(newDescriptor, js);
        return resultHandler;
    }

    private Path of(Ref ref) {
        return ref instanceof WindowDefinition ? Path.WINDOW : pathLookup.apply(ref);
    }

    public class PropertiesHandle {
        private final Ref ref;
        public PropertiesHandle(Ref ref) {
            this.ref = ref;
        }

        public CompletableFuture<String> get(String propertyName) {
            final Integer newDescriptor = descriptorSupplier.get();
            final Path path = of(ref);
            if (path != null) {
                final CompletableFuture<String> valueFuture = new CompletableFuture<>();
                registeredEventHandlers.put(newDescriptor, valueFuture);
                out.extractProperty(newDescriptor, path, propertyName);
                return valueFuture;
            } else {
                return CompletableFuture.failedFuture(new IllegalStateException("Ref not found: " + ref));
            }

        }

        public CompletableFuture<Void> set(String propertyName, String value) {
            final Path path = of(ref);
            if (path != null) {
                out.modifyDom(List.of(new RemoteDomChangesPerformer.SetAttr(path, XmlNs.html, propertyName, value, true)));
                return new CompletableFuture<>();
            } else {
                return CompletableFuture.failedFuture(new IllegalStateException("Ref not found: " + ref));
            }
        }
    }
}
