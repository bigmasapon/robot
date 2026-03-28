**Toy robot code challenge**

    This is spring boot code for answering of topic "Toy robot code challenge" 

**Environment**

    - Spring boot v 3.5.8
    - java version 24
    - apache-maven-3.9.4
    - lombok
  
**Setup & Installation**

    # 1. Clone
    git clone https://github.com/bigmasapon/robot.git
    
    # 2. root
    cd robot
    
    # 3. compile, run & integration test
    mvn clean install
    
    or
    
    # 4. run integration test
    mvn test

**Process flow of program**

    path : src/main/resources/flow/process_flow.jpg

**How to call from postman**

    Endpoint: {url}:55555/execute
    Http method: POST
    request message :
    1. "PLACE" command
      
       {
        "command": "PLACE", -- possible value -> PLACE with meta, MOVE, LEFT, RIGHT, REPORT
        "meta": {
            "coordinator_x": 0,  -- index of array value from 0 - 4 
            "coordinator_y": 0,  -- index of array value from 0 - 4 
            "face": "NORTH"      -- possible value NORTH, SOUTH, WEST, EAST
        }
    }
    
    2. "MOVE" command
    
    {
        "command": "MOVE"
    }
  
    3. "LEFT" command
    {
        "command": "LEFT"
    }
    
    4. "RIGHT" command
    {
        "command": "RIGHT"
    }
    
    5. "REPORT" command
    {
        "command": "REPORT"
    }
