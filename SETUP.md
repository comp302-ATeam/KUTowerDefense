# KU Tower Defense - Development Setup

## Quick Setup (No Downloads Required!)

**All required JAR files are already included in the repository!**

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd KUTowerDefense
   ```

2. **Open in IntelliJ IDEA**
   - The JavaFX and JUnit libraries should be automatically configured
   - If not, follow the manual setup below

## Manual Setup (If Auto-Configuration Fails)

### JavaFX Setup
1. **File → Project Structure → Libraries**
2. **Click + → Java**
3. **Navigate to `libs/javafx-jars/`**
4. **Select all `.jar` files**
5. **Click OK**

### JUnit Setup
1. **File → Project Structure → Libraries**
2. **Click + → Java**
3. **Navigate to `libs/junit-jars/`**
4. **Select all `.jar` files**
5. **Click OK**

### VM Options for Running
Add these VM options when running JavaFX applications:
```
--module-path libs/javafx-jars --add-modules javafx.controls,javafx.fxml,javafx.swing
```

## Project Structure
```
KUTowerDefense/
├── libs/
│   ├── javafx-jars/          # JavaFX JAR files (committed)
│   └── junit-jars/           # JUnit JAR files (committed)
├── src/
│   ├── main/java/
│   └── test/java/
├── .idea/libraries/          # IntelliJ library configs (committed)
└── SETUP.md                  # This file
```

## What's Included
- **JavaFX 21.0.2** - All required modules (base, controls, fxml, graphics, media, swing, web)
- **JUnit 5.10.2** - Complete testing framework (api, engine, platform)

## Troubleshooting

### "JavaFX cannot be resolved" errors
1. Make sure JavaFX library is added to Project Structure
2. Check that `libs/javafx-jars/` contains the JAR files
3. Manually add the library if auto-configuration failed

### "JUnit cannot be resolved" errors
1. Make sure JUnit library is added to Project Structure
2. Check that `libs/junit-jars/` contains the JAR files

### After pulling from Git
If you get dependency errors after pulling:
1. **File → Project Structure → Libraries**
2. **Remove old JavaFX/JUnit libraries**
3. **Re-add from `libs/javafx-jars/` and `libs/junit-jars/`**

## For Team Members
- **Always commit** changes to `.idea/libraries/` folder
- **All dependencies are included** - no downloads needed!
- **JAR files are committed** for consistent versions across team 