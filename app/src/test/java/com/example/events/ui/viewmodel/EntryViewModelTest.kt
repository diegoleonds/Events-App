package com.example.events.ui.viewmodel

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class EntryViewModelTest {
    val viewModel = EntryViewModel()

    @Test
    fun shouldReturnTrueWhenNameIsEmpty() {
        val validName = ""
        assertTrue(viewModel.isNameInvalid(validName))
    }

    @Test
    fun shouldReturnTrueWhenNameIsOnlySpaces() {
        val spaces = "  "
        assertTrue(viewModel.isNameInvalid(spaces))
    }

    @Test
    fun shouldReturnTrueWhenNameIsNull() {
        assertTrue(viewModel.isNameInvalid(null))
    }

    @Test
    fun shouldReturnFalseWhenNameIsntEmpty() {
        val emptyName = "Not empty"
        assertFalse(viewModel.isNameInvalid(emptyName))
    }

    @Test
    fun shouldReturnTrueWhenEmailIsEmpty() {
        val emptyEmail = "";
        assertTrue(viewModel.isEmailInvalid(emptyEmail))
    }

    @Test
    fun shouldReturnTrueWhenEmailIsOnlySpaces() {
        val spaces = "  "
        assertTrue(viewModel.isEmailInvalid(spaces))
    }

    @Test
    fun shouldReturnTrueWhenEmailIsNull() {
        assertTrue(viewModel.isEmailInvalid(null))
    }

    @Test
    fun shouldReturnTrueWhenEmailIsntValid() {
        var invalidEmail = "email";
        assertTrue(viewModel.isEmailInvalid(invalidEmail))

        invalidEmail = "email@"
        assertTrue(viewModel.isEmailInvalid(invalidEmail))

        invalidEmail = "email@invalid"
        assertTrue(viewModel.isEmailInvalid(invalidEmail))

        invalidEmail = "email@invalid."
        assertTrue(viewModel.isEmailInvalid(invalidEmail))
    }
}