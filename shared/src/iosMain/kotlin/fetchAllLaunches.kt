import com.github.kittinunf.result.onFailure
import com.github.kittinunf.result.onSuccess
import fuel.Fuel
import fuel.post
import fuel.serialization.toJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

actual fun fetchAllLaunches(): Flow<List<Launch.Doc>?> = flow {
    val json = Json { ignoreUnknownKeys = true }
    Fuel.post(
        url = "https://api.spacexdata.com/v5/launches/query",
        body = launches,
        headers = mapOf("Content-Type" to "application/json; charset=utf-8")
    ).toJson(
        json = json,
        deserializationStrategy = Launch.serializer()
    ).onSuccess {
        emit(it?.docs)
    }.onFailure {
        println(it)
    }
}