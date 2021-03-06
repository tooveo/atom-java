import com.ironsrc.atom.Event;
import com.ironsrc.atom.IEventStorage;
import com.ironsrc.atom.QueueEventStorage;
import org.junit.Assert;
import org.junit.Test;

public class QueueEventStorageTest {
    @Test
    public void testCreateObject() {
        IEventStorage eventManager = new QueueEventStorage();

        Assert.assertEquals(eventManager.getEvent(""), null);
    }

    @Test
    public void testEventGetAdd() {
        IEventStorage eventManager = new QueueEventStorage();
        String streamName = "test stream";

        Event expectedEvent = new Event(streamName, "test data", "test auth");
        eventManager.addEvent(expectedEvent);

        Event resultEvent = eventManager.getEvent(streamName);

        Assert.assertEquals(expectedEvent.stream_, resultEvent.stream_);
        Assert.assertEquals(expectedEvent.data_, resultEvent.data_);
        Assert.assertEquals(expectedEvent.authKey_, resultEvent.authKey_);
    }
}
