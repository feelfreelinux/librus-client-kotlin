package com.wabadaba.dziennik.vo

import com.wabadaba.dziennik.BaseParseTest
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldEqual
import org.joda.time.LocalDate
import org.junit.Test

class DeserializeTest : BaseParseTest() {
    @Test
    fun shouldDeserializeColors() {
        val colors = parseList("/Colors.json", Color::class)
        val expected = ColorEntity()
        expected.setRawColor("FF1493")
        expected.setId("25")
        expected.setName("deeppink")
        val result = colors.toList()
                .blockingGet()
        result shouldContain expected
    }

    @Test
    fun shouldDeserializeCategories() {
        val categories = parseList("/GradeCategories.json", GradeCategory::class)

        val category = GradeCategoryEntity()
        category.setId("164148")
        category.setName("kartkówka")
        category.setWeight(5)

        val color = ColorEntity()
        color.setId("16")
        category.setColor(color)

        val result = categories.toList().blockingGet()
        result shouldContain category
    }

    @Test
    fun shouldDeserializeGrades() {
        val grades = parseList("/Grades.json", Grade::class)

        val grade = GradeEntity().apply {
            setId("1811988")
            setCategory(GradeCategoryEntity().apply { setId("164148") })
            setAddedBy(TeacherEntity().apply {
                setId("1235106")
            })
            setDate(LocalDate.parse("2016-09-22"))
            setGrade("5")
            setConstituent(true)
        }
        val result = grades.toList().blockingGet()
        result[2] shouldEqual grade
    }

    @Test
    fun shouldDeserializeTeachers() {
        val teachers = parseList("/Teachers.json", Teacher::class)

        val teacher = TeacherEntity().apply {
            setId("12345")
            setFirstName("Tomasz")
            setLastName("Problem")
        }

        val result = teachers.toList().blockingGet()
        result shouldContain teacher
    }

    @Test
    fun shouldDeserializeGradeComments() {
        val comments = parseList("/GradeComments.json", GradeComment::class)

        val comment = GradeCommentEntity().apply {
            setId("340006")
            setText("Analiza obrazu. Praca w grupach.")
            setGrade(GradeEntity().apply { setId("815228") })
            setAddedBy(TeacherEntity().apply { setId("1406771") })
        }

        val result = comments.toList().blockingGet()
        result shouldContain comment
    }
}