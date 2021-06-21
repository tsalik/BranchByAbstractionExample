package com.ktsal.branchbyabstraction;

import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.testing.MigrationTestHelper;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.ktsal.branchbyabstraction.data.QuoteEntity;
import com.ktsal.branchbyabstraction.data.RoomQuotesDatabase;
import com.ktsal.branchbyabstraction.domain.entity.Quote;
import com.squareup.sqlbrite2.BriteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import io.reactivex.observers.TestObserver;

import static com.ktsal.branchbyabstraction.SqlBriteTestHelperKt.addTestQuote;
import static com.ktsal.branchbyabstraction.SqlBriteTestHelperKt.clearTestDb;
import static com.ktsal.branchbyabstraction.SqlBriteTestHelperKt.createTestDb;
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
        Quote wubbaLubbaDubDub = new Quote("wubba lubba dub dub", "Rick Sanchez");
        addTestQuote(briteDatabase, wubbaLubbaDubDub);

        migrationTestHelper.runMigrationsAndValidate("Quotes.db", 2, true, RoomQuotesDatabase.MIGRATION_1_2);
        roomQuotesDatabase = getMigratedRoomDb();
        TestObserver<List<QuoteEntity>> testObserver = roomQuotesDatabase.quotesDao().getSavedQuotes().test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);
        List<QuoteEntity> quoteEntities = testObserver.values().get(0);
        QuoteEntity quoteEntity = quoteEntities.get(0);
        assertThat(quoteEntity.quoteContent, is(equalTo("wubba lubba dub dub")));
        assertThat(quoteEntity.quoteSource, is(equalTo("Rick Sanchez")));
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

    @Test
    public void givenDbIsEmpty_whenMigrateToRoomAndInsertData_thenRoomReturnsTheNewEntry() throws IOException {
        QuoteEntity dontEvenTrip = new QuoteEntity();
        dontEvenTrip.quoteContent = "don't even trip dawg";
        dontEvenTrip.quoteSource = "Rick Sanchez";

        migrationTestHelper.runMigrationsAndValidate("Quotes.db", 2, true, RoomQuotesDatabase.MIGRATION_1_2);
        roomQuotesDatabase = getMigratedRoomDb();
        roomQuotesDatabase.quotesDao().insertQuote(dontEvenTrip);
        TestObserver<List<QuoteEntity>> testObserver = roomQuotesDatabase.quotesDao().getSavedQuotes().test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);
        List<QuoteEntity> quoteEntities = testObserver.values().get(0);
        QuoteEntity quoteEntity = quoteEntities.get(0);
        assertThat(quoteEntity.quoteContent, is(equalTo("don't even trip dawg")));
        assertThat(quoteEntity.quoteSource, is(equalTo("Rick Sanchez")));
    }

    @Test
    public void givenDbNotEmpty_whenMigrateToRoomAndInsertData_thenRoomReturnsOldQuotesPlusTheNew() throws IOException {
        addTestQuote(briteDatabase, new Quote("Weddings are basically funerals with cake", "Rick Sanchez"));
        QuoteEntity existenceIsPain = new QuoteEntity();
        existenceIsPain.quoteContent = "Existence is pain";
        existenceIsPain.quoteSource = "Mr.Meeseeks";

        migrationTestHelper.runMigrationsAndValidate("Quotes.db", 2, true, RoomQuotesDatabase.MIGRATION_1_2);
        roomQuotesDatabase = getMigratedRoomDb();
        roomQuotesDatabase.quotesDao().insertQuote(existenceIsPain);
        TestObserver<List<QuoteEntity>> testObserver = roomQuotesDatabase.quotesDao().getSavedQuotes().test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValueCount(1);
        List<QuoteEntity> quoteEntities = testObserver.values().get(0);
        QuoteEntity weddingsAreFunerals = quoteEntities.get(0);
        assertThat(weddingsAreFunerals.quoteContent, is(equalTo("Weddings are basically funerals with cake")));
        assertThat(weddingsAreFunerals.quoteSource, is(equalTo("Rick Sanchez")));
        QuoteEntity existanceIsPainFromDb = quoteEntities.get(1);
        assertThat(existanceIsPainFromDb.quoteContent, is(equalTo("Existence is pain")));
        assertThat(existanceIsPainFromDb.quoteSource, is(equalTo("Mr.Meeseeks")));
    }

    @After
    public void clearDb() {
        clearTestDb(briteDatabase);
    }

    private RoomQuotesDatabase getMigratedRoomDb() {
        RoomQuotesDatabase roomDb = Room.databaseBuilder(InstrumentationRegistry.getInstrumentation().getTargetContext(), RoomQuotesDatabase.class, "Quotes.db")
                .addMigrations()
                .build();
        migrationTestHelper.closeWhenFinished(roomDb);
        return roomDb;
    }
}
