package io.sentry.adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.TransactionContexts;
import java.lang.reflect.Type;
import java.util.Map;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public final class TransactionContextsSerializerAdapter
    implements JsonSerializer<TransactionContexts> {

  private final @NotNull ILogger logger;

  public TransactionContextsSerializerAdapter(@NotNull final ILogger logger) {
    this.logger = logger;
  }

  @Override
  public JsonElement serialize(
      TransactionContexts src, Type typeOfSrc, JsonSerializationContext context) {
    if (src == null) {
      return null;
    }

    final JsonObject object = new JsonObject();
    for (final Map.Entry<String, Object> entry : src.entrySet()) {
      try {
        final JsonElement element = context.serialize(entry.getValue(), Object.class);
        if (element != null) {
          object.add(entry.getKey(), element);
        }
      } catch (JsonParseException e) {
        logger.log(SentryLevel.ERROR, "%s context key isn't serializable.");
      }
    }
    return object;
  }
}