package io.sentry;

import io.sentry.protocol.Request;
import io.sentry.protocol.SdkVersion;
import io.sentry.protocol.SentryId;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An item sent to Sentry in the envelope. Can be either {@link SentryEvent} or the Performance
 * transaction.
 */
public abstract class SentryBaseEvent<T extends ConcurrentHashMap<String, Object>> {
  private @Nullable SentryId eventId;
  private T contexts;
  private @Nullable SdkVersion sdk;
  private @Nullable Request request;

  protected SentryBaseEvent(final @NotNull SentryId eventId) {
    this.eventId = eventId;
  }

  protected SentryBaseEvent() {
    this(new SentryId());
  }

  public @Nullable SentryId getEventId() {
    return eventId;
  }

  public void setEventId(@Nullable SentryId eventId) {
    this.eventId = eventId;
  }

  public T getContexts() {
    return contexts;
  }

  public void setContexts(T contexts) {
    this.contexts = contexts;
  }

  // todo: add @nullable
  public SdkVersion getSdk() {
    return sdk;
  }

  public void setSdk(@Nullable SdkVersion sdk) {
    this.sdk = sdk;
  }

  public Request getRequest() {
    return request;
  }

  public void setRequest(Request request) {
    this.request = request;
  }
}
