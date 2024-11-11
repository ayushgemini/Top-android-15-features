import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.util.Log

class SyncDataWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Simulate data synchronization task
        Log.d("SyncDataWorker", "Synchronizing data in background...")

        // Perform the sync task here
        // For example, fetch data from network, sync with server, etc.

        return Result.success()
    }
}
