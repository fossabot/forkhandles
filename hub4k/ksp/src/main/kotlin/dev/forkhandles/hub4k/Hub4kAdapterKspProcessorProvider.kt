package dev.forkhandles.hub4k

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class Hub4kAdapterKspProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment) =
        Hub4kAdapterKspProcessor(environment.logger, environment.codeGenerator)
}
