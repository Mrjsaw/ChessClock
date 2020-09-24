package com.example.android.chessclock

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ExampleInstrumentedTest::class,
    MainActivityTest::class,
    NavigationTest::class,
    SettingsActivityTest::class,
    ThemeSettingsTest::class,
    TimersStartOnClickTest::class,
    TimePickerTest::class
)
class TestSuite