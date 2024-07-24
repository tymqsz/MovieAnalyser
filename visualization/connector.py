import pyodbc

class Connector:
    def __init__(self) -> None:
        self.connection = None
        self.cursor = None

        conn_str = (
            "DRIVER={ODBC Driver 18 for SQL Server};"
            "SERVER=xxx;"
            "PORT=1433;"
            "DATABASE=MovieDB;"
            "UID=test;"
            "PWD=test;"
            "TrustServerCertificate=yes;"
        )

        try:
            self.connection = pyodbc.connect(conn_str)
            self.cursor = self.connection.cursor()
        except pyodbc.Error:
            print("connection unsuccesful")
            exit(1)

    def execute_query(self, query: str) -> list:
        self.cursor.execute(query)

        return self.cursor
    





