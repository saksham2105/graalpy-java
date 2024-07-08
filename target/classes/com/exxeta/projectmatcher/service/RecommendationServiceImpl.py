import java
from typing import List


Employee = java.type("com.exxeta.projectmatcher.model.Employee")
Project = java.type("com.exxeta.projectmatcher.model.Project")


class RecommendationServiceImpl:
    @staticmethod
    def recommendEmployees(project: Project, employees: List[Employee]) -> List[Employee]:
        return list([1,2,3])

        
