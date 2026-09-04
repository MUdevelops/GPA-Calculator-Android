# Implementation Plan - GPA786

Building a modern, native Android application for calculating and tracking GPA and CGPA, with a focus on Material 3 design and responsive layouts.

## User Review Required

> [!IMPORTANT]
> The default grading scale will follow the standard 4.0 system (A+=4.0, A=4.0, A-=3.67, etc.). I will make this configurable in Settings, but the first launch will use these values.

> [!NOTE]
> I will implement "Built by Your Colleague M. Umar Jamal" and "Welcome BSCS Friends" branding as requested.

## Proposed Changes

### 1. Project Infrastructure & Configuration

- Update `libs.versions.toml` with Room, Navigation, DataStore, and Material Icons Extended.
- Configure `app/build.gradle.kts` with new dependencies and KSP (for Room).
- Establish package structure: `data`, `domain`, `ui`, `navigation`, `util`.

### 2. Core Domain Logic (Calculations)

- [NEW] `GradeCalculator.kt`: Handles mapping marks/percentages to grades and grade points based on a scale.
- [NEW] `GPAMathematics.kt`: Pure functions for calculating GPA and CGPA (weighted averages).
- [NEW] `WeightCalculator.kt`: Logic for component-based weighted marks.

### 3. Data Layer (Persistence)

- [NEW] `AppDatabase.kt`: Room database for storing `Semester` and `Course` entities.
- [NEW] `SemesterDao.kt`: Data access for academic records.
- [NEW] `PreferencesRepository.kt`: DataStore implementation for Settings (Theme, Grade Scale, etc.).

### 4. Navigation & Adaptive UI

- [NEW] `NavGraph.kt`: Define destinations (Dashboard, GPA Calc, Records, Scale, Settings).
- [NEW] `AdaptiveNavigation.kt`: Implementation of BottomBar vs NavRail based on `WindowSizeClass`.

### 5. UI Components & Screens

- [MODIFY] [MainActivity.kt](file:///D:/GpaCalculatorBSCS6th/app/src/main/java/com/craftxcode/gpacalculatorbscs6th/MainActivity.kt): Set up `WindowSizeClass` and `AppContent`.
- [NEW] `Theme.kt` & `Color.kt`: Material 3 theme with the requested colorful gradient palette.
- [NEW] `SplashScreen.kt`: Animated onboarding experience.
- [NEW] `DashboardScreen.kt`: Statistics overview and quick actions.
- [NEW] `GPACalculatorScreen.kt`: Input courses, marks, and view results.
- [NEW] `ComponentWeightScreen.kt`: Advanced weighted marks calculator.
- [NEW] `AcademicRecordsScreen.kt`: History of saved semesters.
- [NEW] `SettingsScreen.kt`: Configuration options.

### 6. Polish & Finalization

- Implement animations using `AnimatedVisibility` and `animate*AsState`.
- Add input validation and error handling for all forms.
- Create unit tests for calculation logic.

## Verification Plan

### Automated Tests
- `GPAMathematicsTest.kt`: Verify correct GPA/CGPA results for various edge cases.
- `GradeCalculatorTest.kt`: Verify grade mapping for default and custom scales.

### Manual Verification
- Deploy to emulator and verify responsive behavior (resizing windows).
- Verify dark/light mode switching.
- Verify data persistence after app restart.
