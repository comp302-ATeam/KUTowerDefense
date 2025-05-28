# KU Tower Defense - Team Setup Guide

## 🎯 **Zero-Configuration Setup**

This project is configured to work **automatically** for all team members. No manual library setup required!

## 📋 **Quick Start**

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd KUTowerDefense
   ```

2. **Open in IntelliJ IDEA:**
   - File → Open → Select the `KUTowerDefense` folder
   - IntelliJ will automatically detect and configure all dependencies

3. **Verify setup:**
   - JavaFX imports should work immediately
   - JUnit tests should run without configuration

## 🔧 **What's Included**

- **JavaFX 21** - All required JAR files in `libs/javafx-jars/`
- **JUnit 5** - Testing framework in `libs/junit-jars/`
- **Automatic Configuration** - Dependencies configured in `302Project.iml`

## ▶️ **Running the Project**

### Main Application:
```bash
java --module-path libs/javafx-jars --add-modules javafx.controls,javafx.fxml -cp "out/production/302Project:libs/javafx-jars/*" main.java.Main
```

### Tests:
- Right-click any test class → "Run Tests"
- Or use: Run → Run All Tests

## 🚨 **Troubleshooting**

### If JavaFX imports show errors:
1. **Refresh project:** File → Reload Gradle Project (or equivalent)
2. **Rebuild:** Build → Rebuild Project
3. **Check JDK:** File → Project Structure → Project → ensure JDK 11+ is selected

### If tests don't run:
1. **Verify test scope:** Right-click `src/test/java` → Mark Directory as → Test Sources Root
2. **Check JUnit:** The JUnit libraries should appear under External Libraries

### If nothing works:
1. **Close IntelliJ completely**
2. **Delete `.idea` folder** (this will reset IntelliJ settings)
3. **Reopen the project** - IntelliJ will recreate settings and detect dependencies

## 📁 **Project Structure**

```
KUTowerDefense/
├── src/
│   ├── main/java/          # Source code
│   ├── main/resources/     # Assets, FXML files
│   └── test/java/          # Test files
├── libs/
│   ├── javafx-jars/        # JavaFX dependencies (auto-configured)
│   └── junit-jars/         # JUnit dependencies (auto-configured)
├── 302Project.iml          # IntelliJ module file (contains all config)
└── SETUP.md               # This file
```

## ✅ **Success Indicators**

When setup is working correctly:
- ✅ No red underlines on `import javafx.*` statements
- ✅ No red underlines on `import org.junit.*` statements  
- ✅ "External Libraries" shows JavaFX and JUnit entries
- ✅ Tests run successfully

## 🤝 **Team Workflow**

1. **Pull latest changes:** `git pull`
2. **Code your features**
3. **Run tests:** Ensure all tests pass
4. **Commit and push:** `git add . && git commit -m "message" && git push`

**No additional setup steps needed!** 🎉 