package com.ktsal.branchbyabstraction;

import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.ktsal.branchbyabstraction.data.QuoteEntity;
import com.ktsal.branchbyabstraction.data.RoomQuotesDatabase;
import com.ktsal.branchbyabstraction.domain.entity.Quote;
import com.squareup.sqlbrite2.BriteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import io.reactivex.observers.TestObserver;

import static com.ktsal.branchbyabstraction.SqlBriteTestHelperKt.addTestQuote;
import static com.ktsal.branchbyabstraction.SqlBriteTestHelperKt.clearTestDb;
import static com.ktsal.branchbyabstraction.SqlBriteTestHelperKt.createTestDb;
import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(AndroidJUnit4.class)
public class RoomMigrationTest {

    @Rule
    public MigrationTestHelper migrationTestHelper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
            RoomQuotesDatabase.class.getCanonicalName(), new FrameworkSQLiteOpenHelperFactory());

    private BriteDatabase briteDatabase;
    private RoomQuotesDatabase roomQuotesDatabase;

    @Before
    public void setupDb() {
        briteDatabase = createTestDb(1);
        clearTestDb(briteDatabase);
    }

    @Test
    public void givenDbNotEmpty_whenMigrateToRoom_thenRoomMaintainsQuotes() throws IOException {
        Quote quote = new Quote("wubba lubba dub dub", "Rick");
        addTestQuote(briteDatabase, quote);

        migrationTestHelper.runMigrationsAndValidate("Quotes.db", 2, true, RoomQuotesDatabase.MIGRATION_1_2);
        roomQuotesDatabase = getMigratedRoomDb();
        TestObserver<List<QuoteEntity>> testObserver = roomQuotesDatabase.quotesDao().getSavedQuotes().test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);
        List<QuoteEntity> quoteEntities = testObserver.values().get(0);
        QuoteEntity quoteEntity = quoteEntities.get(0);
        assertThat(quoteEntity.quoteContent, is(equalTo("wubba lubba dub dub")));
        assertThat(quoteEntity.quoteSource, is(equalTo("Rick")));
    }

    @Test
    public void givenDbIsEmpty_whenMigrateToRoom_thenRoomReturnsEmptyQuotes() throws IOException {

        migrationTestHelper.runMigrationsAndValidate("Quotes.db", 2, true, RoomQuotesDatabase.MIGRATION_1_2);
        roomQuotesDatabase = getMigratedRoomDb();
        TestObserver<List<QuoteEntity>> testObserver = roomQuotesDatabase.quotesDao().getSavedQuotes().test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);
        List<QuoteEntity> quoteEntities = testObserver.values().get(0);
        assertThat(quoteEntities, is(empty()));
    }

    private RoomQuotesDatabase getMigratedRoomDb() {
        RoomQuotesDatabase roomDb = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(), RoomQuotesDatabase.class, "Quotes.db")
                .addMigrations()
                .build();
        migrationTestHelper.closeWhenFinished(roomDb);
        return roomDb;
    }
}
