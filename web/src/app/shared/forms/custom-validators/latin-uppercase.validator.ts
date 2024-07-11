import { AbstractControl, ValidationErrors } from '@angular/forms';

// @ts-ignore
import XRegExp from 'xregexp';

export class LatinUppercaseValidator {
  private static readonly regex = XRegExp("^(?:([A-Z]+(([ ]|[']|[\\-]|[_])?[A-Z])*)*|null)$");

  public static validator(control: AbstractControl): ValidationErrors | null {
    if (LatinUppercaseValidator.regex.test(control.value) || control.value == '') {
      return null;
    }

    return { latinUppercase: true };
  }
}
