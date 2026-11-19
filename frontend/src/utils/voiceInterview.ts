import type { SkillDTO } from '../api/skill.ts';

export function getTemplateName(skillId: string, skills: SkillDTO[]): string {
  return skills.find(s => s.id === skillId)?.name || skillId;
}
