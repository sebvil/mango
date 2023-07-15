./gradlew ktlintFormat
git add -A && git commit -m "lint"
if ./gradlew testDebugUnitTest
then
    gt stack submit
else
    echo "Grade failed with exit status $gradlew_return_code" >&2
    echo "and output: $gradlew_output" >&2
    echo "Aborting pull request." >&2
fi