import java
from typing import List


Employee = java.type("com.exxeta.projectmatcher.model.Employee")
Project = java.type("com.exxeta.projectmatcher.model.Project")


class RecommendationServiceImpl:
    @staticmethod
    def recommendEmployees(project: Project, employees: List[Employee]) -> List[Employee]:
        recommeneded_emps = []
        print("I am about to run....")
        for emp in employees:
            recommeneded_emps.append(emp)
        return recommeneded_emps


