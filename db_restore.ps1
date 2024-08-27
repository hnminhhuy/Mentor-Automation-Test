# PostgreSQL Database Credentials
$PGUSER = "postgres"
$PGPASSWORD = "12345"
$PGDATABASE = "mentorus"

# Resolve the relative path to an absolute path
$BACKUP_FILE = "D:\uni\MentorUS\test\MentorUS - Automation test\dbBackup\backup_20240825_121329.dump"

# Run the pg_restore command to restore the backup
$env:PGPASSWORD = $PGPASSWORD
& "C:\Program Files\PostgreSQL\16\bin\pg_restore.exe" -U $PGUSER -d $PGDATABASE -v --clean --if-exists $BACKUP_FILE

Write-Output "Database restored from $BACKUP_FILE"