import com.eatthepath.pushy.apns.ApnsClient
import com.eatthepath.pushy.apns.ApnsClientBuilder
import com.eatthepath.pushy.apns.auth.ApnsSigningKey
import com.eatthepath.pushy.apns.util.SimpleApnsPayloadBuilder
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification
import java.io.InputStream
import java.nio.file.Paths
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import kotlin.random.Random


internal class NotificationServiceConfig(


    private val apnsServerHost: String = "api.sandbox.push.apple.com",

    private val apnsServerPort: Int = 443,

    private val apnsKeyPath: String = "classpath:test-signing-key.p8",

    private val keyId: String = "Key ID",

    private val teamId: String = "Team ID",

    private val retryCount: Int = 3,

    private val retryDelayMillis: Long = 50,

    private val topic: String = "topic",

    private val trustedServerCertificateChainPath: String? = "ca.pem"
) {

    fun apnsClient(): ApnsClient = ApnsClientBuilder()
        .setApnsServer(apnsServerHost, apnsServerPort)
        .setSigningKey(
            ApnsSigningKey.loadFromInputStream(
                findFile(apnsKeyPath),
                teamId,
                keyId
            )
        )
        .build()


    private fun findFile(path: String): InputStream {
        val classPathPrefix = "classpath:"
        if (path.startsWith(classPathPrefix)) {
            val resourcePath = path.removePrefix(classPathPrefix)
            return this.javaClass.classLoader.getResourceAsStream(resourcePath)
                ?: error("cannot find resource: $resourcePath")
        }
        return Paths.get(path).toFile().inputStream()
    }
}

fun main() {
    val client = NotificationServiceConfig().apnsClient()


    val apnsToken = "1234567890abcdef${Random.nextLong()}"

    val invalidationTime = null
    val collapseId: String? = null


    val payload = SimpleApnsPayloadBuilder().apply {
        setAlertBody("Test message")
    }.build()

    val pushNotification = SimpleApnsPushNotification(
        apnsToken,
        "topic",
        payload,
        invalidationTime,
        null,
        collapseId
    )

    val response = runBlocking {
        client.sendNotification(pushNotification).await()
    }

    println(response.isAccepted)
    println(response)
}
