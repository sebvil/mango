./gradlew ktlintFormat
git add -A && git commit -m "lint"
if ./gradlew testDebugUnitTest
then
    gt upstack submit
else
    echo "Tests failed, aborting pull request."
fi