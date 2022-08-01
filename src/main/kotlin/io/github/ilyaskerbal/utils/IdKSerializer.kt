package io.github.ilyaskerbal.utils

import io.github.ilyaskerbal.domain.model.User
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import org.litote.kmongo.Id
import org.litote.kmongo.id.serialization.IdKotlinXSerializationModule

//object IdKSerializer : KSerializer<User> {
//    override val descriptor: SerialDescriptor
//        get() = PrimitiveSerialDescriptor("_id", PrimitiveKind.STRING)
//
//    override fun serialize(encoder: Encoder, value: User) {
//        val json = Json { serializersModule = IdKotlinXSerializationModule }
//        encoder.encodeString(value.toString())
//    }
//
//    override fun deserialize(decoder: Decoder): User {
//        val stringId = decoder.decodeString()
//    }
//}