package io.sentry;

import io.sentry.protocol.Contexts;
import io.sentry.protocol.SentryId;
import org.jetbrains.annotations.NotNull;

public final class TransactionContexts extends Contexts {
  private static final long serialVersionUID = 252445813254943011L;

  public TransactionContexts() {
    this(new TraceContext());
  }

  private TransactionContexts(final @NotNull TraceContext traceContext) {
    this.setTraceContext(traceContext);
  }

  /**
   * Creates {@link TransactionContexts} from sentry-trace header.
   *
   * @param sentryTrace - the sentry-trace header
   * @return the transaction contexts
   */
  public static @NotNull TransactionContexts fromSentryTrace(final @NotNull String sentryTrace)
      throws InvalidSentryTraceHeaderException {
    final String[] parts = sentryTrace.split("-", -1);
    if (parts.length < 2) {
      throw new InvalidSentryTraceHeaderException(sentryTrace);
    }
    return new TransactionContexts(
        new TraceContext(new SentryId(parts[0]), new SpanId(), new SpanId(parts[1])));
  }

  public TraceContext getTraceContext() {
    return toContextType(TraceContext.TYPE, TraceContext.class);
  }

  public void setTraceContext(final @NotNull TraceContext traceContext) {
    this.put(TraceContext.TYPE, traceContext);
  }
}