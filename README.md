# Pain Pattern Analyzer — Android App Plan

## Tech Stack

- Kotlin
- Jetpack Compose
- MVVM
- Room Database
- Navigation Compose
- Material 3

---

# Project Goal

Build a native Android app for tracking pain patterns over time.

The app should allow users to:
- log pain entries
- view history
- visualize trends
- identify simple correlations

This is NOT a medical diagnosis app.

Keep implementation clean and minimal.
Do not add unnecessary features.

---

# Core Features Only

## 1. Add Pain Entry

Each entry should contain:

- pain level (1–10)
- body area
- pain type
- stress level
- sleep hours
- notes
- timestamp

Use:
- slider for pain level
- dropdowns where appropriate

---

## 2. Timeline Screen

Display all entries in chronological order.

Requirements:
- LazyColumn
- grouped by date
- clean card UI
- simple filtering by body area

---

## 3. Dashboard Screen

Show:
- average pain level
- total entries
- recent entries
- weekly trend chart

Keep charts simple.

---

## 4. Insights Screen

Generate only simple insights using local calculations.

Examples:
- higher pain on low sleep days
- common pain areas
- average stress during severe pain
---

# Folder Structure

Use clean architecture:

```text
data/
    local/
    repository/

model/

ui/
    screens/
    components/

viewmodel/
```

---
