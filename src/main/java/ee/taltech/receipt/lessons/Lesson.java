package ee.taltech.receipt.lessons;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.List;

@Getter
@Setter
public class Lesson {

    private String name;
    private Long course;
    private Duration duration;
    private String description;
    private List<String> materials;
    //    ... many more
    private List<Students> students;
}
