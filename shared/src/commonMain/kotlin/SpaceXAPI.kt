import kotlinx.coroutines.flow.Flow

expect fun fetchAllLaunches(): Flow<List<Launch.Doc>?>