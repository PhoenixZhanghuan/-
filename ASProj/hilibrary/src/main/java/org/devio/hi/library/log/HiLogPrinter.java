package org.devio.hi.library.log;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public interface HiLogPrinter {
    void print(@NotNull HiLogConfig config, int level, String tag, @NonNull String printString);
}
